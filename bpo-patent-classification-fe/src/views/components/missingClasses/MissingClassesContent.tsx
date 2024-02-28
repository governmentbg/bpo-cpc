import React, { useEffect } from "react";
import { Box, CardContent } from "@mui/material";
import { BaseInputFieldFilter, BorderGreyBox, CardSpg, isNotEmpty, useReactHookForm } from "@duosoftbg/bpo-components";
import { FormProvider } from "react-hook-form";
import { useStore } from "react-redux";
import MissingClassesList from "./MissingClassesList";
import { emptyValidationSchema } from "../../../yup/schema/emptyValidationSchema";
import SearchFilters from "../common/search/filters/SearchFilters";
import { SEARCH_FILTERS_GROUP } from "../../../config/search/filters/groupsConfig";
import { IpcFilterDetails } from "../../../types/types";
import { ipcFilterInitialValues } from "../../../init/ipcFilterInitialValues";
import useSearchTableControl from "../../../hooks/search/useSearchTableControl";
import { missingClassesSearch } from "../../../axios/api/services";

const MissingClassesContent = () => {
  const store = useStore();
  const group = SEARCH_FILTERS_GROUP.MISSING_CLASSES;

  const { methods, handleSubmit } = useReactHookForm<IpcFilterDetails>({
    defaultValues: ipcFilterInitialValues,
    validationSchema: emptyValidationSchema,
  });

  useEffect(() => {
    const filtersData = store.getState().SearchData.backofficeSearchTable[group].filtersData;
    if (isNotEmpty(filtersData)) {
      methods.reset(filtersData);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const control = useSearchTableControl({
    group,
    methods,
    initialValues: ipcFilterInitialValues,
    filterData: missingClassesSearch,
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
                    <BaseInputFieldFilter fieldName={"ipcEditionCode"} label={"l.ipcEditionCode"} md={4} sm={6} />
                    <BaseInputFieldFilter fieldName={"ipcSectionCode"} label={"l.ipcSectionCode"} md={4} sm={6} />
                    <BaseInputFieldFilter fieldName={"ipcClassCode"} label={"l.ipcClassCode"} md={4} sm={6} />
                    <BaseInputFieldFilter fieldName={"ipcSubclassCode"} label={"l.ipcSubclassCode"} md={4} sm={6} />
                    <BaseInputFieldFilter fieldName={"ipcGroupCode"} label={"l.ipcGroupCode"} md={4} sm={6} />
                    <BaseInputFieldFilter fieldName={"ipcSubgroupCode"} label={"l.ipcSubgroupCode"} md={4} sm={6} />
                    <BaseInputFieldFilter fieldName={"ipcName"} label={"l.ipcName"} md={12} sm={12} />
                  </SearchFilters>
                </BorderGreyBox>
                {control.showList && (
                  <MissingClassesList
                    records={control.records}
                    total={control.total}
                    isLoading={control.isLoading}
                    onPageOrOrderChange={control.handlePageOrOrderChange}
                    blockTable={control.blockTable}
                    group={group}
                    filterFn={control.fetch}
                  />
                )}
              </Box>
            </CardContent>
          </CardSpg>
        </form>
      </FormProvider>
    </>
  );
};

export default MissingClassesContent;
