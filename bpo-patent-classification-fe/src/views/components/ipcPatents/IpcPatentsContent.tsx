import React, { useEffect, useState } from "react";
import { Box, Button, CardContent } from "@mui/material";
import {
  AsyncCallArgs,
  BaseInputFieldFilter,
  BaseSelectFieldFilter,
  BorderGreyBox,
  CardSpg,
  GridContainer,
  GridItem,
  isNotEmpty,
  removeAllIds,
  ScrollableAsyncFormAutocomplete,
  SimpleConfirmDialog,
  useAsyncCall,
  useReactHookForm,
} from "@duosoftbg/bpo-components";
import { FormProvider } from "react-hook-form";
import { useStore } from "react-redux";
import IpcPatentsList from "./IpcPatentsList";
import { emptyValidationSchema } from "../../../yup/schema/emptyValidationSchema";
import SearchFilters from "../common/search/filters/SearchFilters";
import { SEARCH_FILTERS_GROUP } from "../../../config/search/filters/groupsConfig";
import { IpcPatentsFilterDetails } from "../../../types/types";
import useSearchTableControl from "../../../hooks/search/useSearchTableControl";
import { ipcPatentsFilterInitialValues } from "../../../init/ipcPatentsFilterInitialValues";
import {
  classifyAll,
  classifyAllMissing,
  classifyMultiple,
  getIpcAutocomplete,
  ipcPatentsSearch,
} from "../../../axios/api/services";
import { useParams } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { toast } from "react-toastify";
import useAppDispatch from "../../../hooks/redux/base/useAppDispatch";

const IpcPatentsContent = ({ currentIpc, latestIpc }) => {
  const store = useStore();
  const params = useParams();
  const { t } = useTranslation();
  const dispatch = useAppDispatch();
  const group = SEARCH_FILTERS_GROUP.IPC_PATENTS;

  const { methods, handleSubmit } = useReactHookForm<IpcPatentsFilterDetails>({
    defaultValues: { ...ipcPatentsFilterInitialValues, symbol: params.symbol, ipcEditionCode: params.edition },
    validationSchema: emptyValidationSchema,
  });

  useEffect(() => {
    dispatch(removeAllIds());
    const filtersData = store.getState().SearchData.backofficeSearchTable[group].filtersData;
    if (isNotEmpty(filtersData)) {
      methods.reset(filtersData);
      methods.setValue("symbol", params.symbol);
      methods.setValue("ipcEditionCode", params.edition);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const control = useSearchTableControl({
    group,
    methods,
    initialValues: { ...ipcPatentsFilterInitialValues, symbol: params.symbol, ipcEditionCode: params.edition },
    filterData: ipcPatentsSearch,
    callAfterResetFilters: true,
  });

  return (
    <>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(() => control.handleSubmitFilters())}>
          <CardSpg my={4} style={{ overflow: "visible" }}>
            <CardContent style={{ position: "relative" }}>
              <Box>
                <BorderGreyBox>
                  <SearchFilters
                    isSubmitBtnDisabled={control.isSubmitBtnDisabled}
                    isResetBtnDisabled={control.isResetBtnDisabled}
                    handleResetFilters={control.handleResetFilters}
                  >
                    <BaseSelectFieldFilter
                      addEmptyOption={true}
                      options={[
                        { text: t("l.patent"), value: "P" },
                        { text: t("l.utility.model"), value: "U" },
                        { text: t("l.eu.patent"), value: "T" },
                      ]}
                      fieldName={"fileTyp"}
                      label={"l.fileTyp"}
                      md={4}
                      sm={6}
                    />
                    <BaseInputFieldFilter fieldName={"fileSer"} label={"l.fileSer"} md={4} sm={6} />
                    <BaseInputFieldFilter fieldName={"fileNbr"} label={"l.fileNbr"} md={4} sm={6} />
                    <BaseInputFieldFilter fieldName={"regNum"} label={"l.regNum"} md={4} sm={6} />
                    <BaseInputFieldFilter fieldName={"patentTitle"} label={"l.patentTitle"} md={8} sm={12} />
                  </SearchFilters>
                </BorderGreyBox>
                {control.showList && (
                  <>
                    <IpcPatentsList
                      records={control.records}
                      total={control.total}
                      isLoading={control.isLoading}
                      onPageOrOrderChange={control.handlePageOrOrderChange}
                      blockTable={control.blockTable}
                      group={group}
                      showCheckboxes={isNotEmpty(latestIpc?.ipcSectionCode)}
                    />
                    {latestIpc?.ipcSectionCode && control.total > 0 && (
                      <OldVersionsButtons currentIpc={currentIpc} latestIpc={latestIpc} control={control} />
                    )}
                    {!latestIpc?.ipcSectionCode && control.total > 0 && (
                      <MissingClassesButtons currentIpc={currentIpc} control={control} />
                    )}
                  </>
                )}
              </Box>
            </CardContent>
          </CardSpg>
        </form>
      </FormProvider>
    </>
  );
};

const MissingClassesButtons = ({ currentIpc, control }) => {
  const { t } = useTranslation();
  const { asyncCall } = useAsyncCall();

  const [openAllMissing, setOpenAllMissing] = useState(false);
  const [symbolForMissing, setSymbolForMissing] = useState<string>(null);

  const onClickClassifyAllMissingBtn = () => {
    const asyncCallArgs: AsyncCallArgs = {
      withGlobalBackdrop: true,
      promise: classifyAllMissing(
        currentIpc?.ipcEditionCode,
        currentIpc?.ipcSectionCode +
          currentIpc?.ipcClassCode +
          currentIpc?.ipcSubclassCode +
          currentIpc?.ipcGroupCode +
          "-" +
          currentIpc?.ipcSubgroupCode,
        symbolForMissing
      ),
      onSuccess: () => {
        toast.success(t("m.classification.success"));
        control.handleSubmitFilters();
      },
      onError: () => {},
    };
    asyncCall(asyncCallArgs);
    setOpenAllMissing(false);
  };

  return (
    <>
      <GridContainer spacing={4} mt={0}>
        <GridItem sm={12} md={12}>
          <ScrollableAsyncFormAutocomplete
            freeSolo={false}
            fieldName={`ipcAutocomplete`}
            setOptionText={(option) => option.name}
            autocompleteFn={getIpcAutocomplete}
            label={"l.ipc.autocomplete"}
            reduceOptionObject={false}
            getOptionLabel={(option) => option.id + ""}
            setInputOnSelect={(option) => {
              setSymbolForMissing(option.name.split(" - ")[0]);
              return option.name;
            }}
            required={true}
            onChangeSelectedAdditional={(option) => {
              if (!option) {
                setSymbolForMissing(null);
              }
            }}
          />
        </GridItem>
        <GridItem sm={6} md={6}>
          <Button
            variant="contained"
            color={"error"}
            size={"medium"}
            onClick={() => setOpenAllMissing(true)}
            disabled={!symbolForMissing}
          >
            {t("l.classify.all.missing")}
          </Button>
        </GridItem>
      </GridContainer>
      <SimpleConfirmDialog
        open={openAllMissing}
        setOpen={setOpenAllMissing}
        dialogTitleText={"t.classify.all.dialog"}
        alertText={"m.classify.all.warning.message"}
        alertType={"warning"}
        onConfirm={onClickClassifyAllMissingBtn}
      ></SimpleConfirmDialog>
    </>
  );
};

const OldVersionsButtons = ({ currentIpc, latestIpc, control }) => {
  const store = useStore();
  const { t } = useTranslation();
  const dispatch = useAppDispatch();
  const { asyncCall } = useAsyncCall();

  const [openAll, setOpenAll] = useState(false);
  const [openSelected, setOpenSelected] = useState(false);

  const onClickClassifyMultipleBtn = () => {
    const newIds = store.getState().LibraryComponentsControl.selectIdsCheckboxTableControl.newIds;
    const asyncCallArgs: AsyncCallArgs = {
      withGlobalBackdrop: true,
      promise: classifyMultiple(
        newIds,
        latestIpc?.ipcSectionCode +
          latestIpc?.ipcClassCode +
          latestIpc?.ipcSubclassCode +
          latestIpc?.ipcGroupCode +
          "-" +
          latestIpc?.ipcSubgroupCode,
        currentIpc?.ipcEditionCode,
        latestIpc?.ipcEditionCode
      ),
      onSuccess: () => {
        dispatch(removeAllIds());
        toast.success(t("m.classification.success"));
        control.handleSubmitFilters();
      },
      onError: () => {},
    };
    if (newIds?.length > 0) asyncCall(asyncCallArgs);
    setOpenSelected(false);
  };
  const onClickClassifyAllBtn = () => {
    const asyncCallArgs: AsyncCallArgs = {
      withGlobalBackdrop: true,
      promise: classifyAll(
        latestIpc?.ipcSectionCode +
          latestIpc?.ipcClassCode +
          latestIpc?.ipcSubclassCode +
          latestIpc?.ipcGroupCode +
          "-" +
          latestIpc?.ipcSubgroupCode,
        currentIpc?.ipcEditionCode,
        latestIpc?.ipcEditionCode
      ),
      onSuccess: () => {
        dispatch(removeAllIds());
        toast.success(t("m.classification.success"));
        control.handleSubmitFilters();
      },
      onError: () => {},
    };
    asyncCall(asyncCallArgs);
    setOpenAll(false);
  };

  return (
    <>
      <Button
        variant="contained"
        size={"small"}
        onClick={() => {
          const newIds = store.getState().LibraryComponentsControl.selectIdsCheckboxTableControl.newIds;
          if (newIds.length > 0) {
            setOpenSelected(true);
          }
        }}
      >
        {t("l.classify.selected")}
      </Button>
      <Button
        style={{ marginLeft: "15px" }}
        variant="contained"
        color={"error"}
        size={"small"}
        onClick={() => setOpenAll(true)}
      >
        {t("l.classify.all")}
      </Button>
      <SimpleConfirmDialog
        open={openSelected}
        setOpen={setOpenSelected}
        dialogTitleText={"t.classify.selected.dialog"}
        alertText={"m.classify.selected.warning.message"}
        alertType={"warning"}
        onConfirm={onClickClassifyMultipleBtn}
      ></SimpleConfirmDialog>
      <SimpleConfirmDialog
        open={openAll}
        setOpen={setOpenAll}
        dialogTitleText={"t.classify.all.dialog"}
        alertText={"m.classify.all.warning.message"}
        alertType={"warning"}
        onConfirm={onClickClassifyAllBtn}
      ></SimpleConfirmDialog>
    </>
  );
};

export default IpcPatentsContent;
