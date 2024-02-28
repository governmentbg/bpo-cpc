import { useStore } from "react-redux";
import { useTranslation } from "react-i18next";
import { useEffect, useReducer } from "react";
import { toast } from "react-toastify";
import {
  AsyncCallArgs,
  reducerInitialState,
  ReloadWatcherObject,
  searchTableReducer,
  useAsyncCall,
  useReloadWatcherReader,
} from "@duosoftbg/bpo-components";
import { UseFormReturn } from "react-hook-form";
import { GroupName } from "../../types/filters";
import useAppDispatch from "../redux/base/useAppDispatch";
import { overrideFiltersData, updateHasSearchStarted } from "../../store/redux/slice/SearchData/backofficeSearchTable";

type SearchTableControlProps = {
  group: GroupName;
  methods: UseFormReturn<any, any>;
  initialValues: any;
  filterData: (requestFilters: any) => any;
  afterResetFilters?: Function;
  callAfterResetFilters?: boolean;
  initialCall?: boolean;
  withGlobalBackdrop?: boolean;
};

const useSearchTableControl = ({
  group,
  methods,
  initialValues,
  filterData,
  afterResetFilters,
  callAfterResetFilters = false,
  initialCall = true,
  withGlobalBackdrop = false,
}: SearchTableControlProps) => {
  const store = useStore();
  const { t } = useTranslation();
  const dispatch = useAppDispatch();
  const { asyncCall } = useAsyncCall();

  const { reloadWatcher } = useReloadWatcherReader(ReloadWatcherObject.SearchTable.fetch());

  const [{ records, total, showList, isLoading, isResetBtnDisabled, isSubmitBtnDisabled, blockTable }, dispatchLocal] =
    useReducer(searchTableReducer, reducerInitialState);

  const handleResetFilters = () => {
    dispatch(overrideFiltersData({ group, data: initialValues }));
    dispatchLocal({ type: "disableResetFiltersButton" });
    methods.reset(initialValues, { keepIsSubmitted: true, keepSubmitCount: true });
    if (afterResetFilters) {
      afterResetFilters();
    }
    dispatchLocal({ type: "afterResetFilters" });
    if (callAfterResetFilters) {
      dispatchLocal({ type: "beforeFetch" });
      fetch();
    }
    toast.info(t("m.resetFilters.success"));
  };

  const fetch = (showToast = false) => {
    const asyncCallArgs: AsyncCallArgs = {
      promise: filterData(methods.getValues()),
      withGlobalBackdrop: withGlobalBackdrop,
      onSuccess: (response) => {
        dispatchLocal({
          type: "afterFetchSuccess",
          payload: { records: response.content, total: response.totalElements },
        });
        if (showToast) {
          toast.success(t("m.submitFilters.success"));
        }
      },
      onError: () => {
        dispatchLocal({ type: "afterFetchError" });
      },
    };
    asyncCall(asyncCallArgs);
  };

  useEffect(() => {
    const hasStarted = store.getState().SearchData.backofficeSearchTable[group].hasSearchStarted;
    if (initialCall && hasStarted) {
      dispatchLocal({ type: "beforeFetch" });
      fetch();
    }
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    if (reloadWatcher) {
      dispatchLocal({ type: "beforeFetch" });
      fetch();
    }
    // eslint-disable-next-line
  }, [reloadWatcher]);

  const handlePageOrOrderChange = () => {
    dispatchLocal({ type: "blockTable" });
    dispatch(overrideFiltersData({ group, data: methods.getValues() }));
    fetch();
  };

  const handleSubmitFilters = (showToast = true) => {
    const resetOrderAndPageData = () => {
      methods.setValue("page", initialValues.page);
      methods.setValue("pageSize", initialValues.pageSize);
      methods.setValue("order", initialValues.order);
      methods.setValue("orderBy", initialValues.orderBy);
    };

    dispatchLocal({ type: "disableSubmitFiltersButton" });
    dispatchLocal({ type: "beforeFetch" });
    resetOrderAndPageData();
    dispatch(updateHasSearchStarted({ group, start: true }));
    dispatch(overrideFiltersData({ group, data: methods.getValues() }));
    fetch(showToast);
  };

  return {
    records,
    total,
    showList,
    isLoading,
    isResetBtnDisabled,
    isSubmitBtnDisabled,
    blockTable,
    handleResetFilters,
    handleSubmitFilters,
    handlePageOrOrderChange,
    fetch,
  };
};

export default useSearchTableControl;
