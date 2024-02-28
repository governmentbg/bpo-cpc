import React from "react";
import { useTranslation } from "react-i18next";
import { Grid } from "@mui/material";
import Box from "@mui/material/Box";
import { MediumButton, MediumGreyButton, WithChildren } from "@duosoftbg/bpo-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch, faClose } from "@fortawesome/free-solid-svg-icons";

const SearchFiltersForm = ({ children }: WithChildren) => {
  return (
    <Grid container spacing={5} px={2} py={6}>
      {children}
    </Grid>
  );
};

const MemoizedSearchFiltersForm = React.memo(SearchFiltersForm);

type SearchFiltersProps = WithChildren & {
  handleResetFilters: any;
  isSubmitBtnDisabled: boolean;
  isResetBtnDisabled: boolean;
};

const SearchFilters = ({
  handleResetFilters,
  isSubmitBtnDisabled,
  isResetBtnDisabled,
  children,
}: SearchFiltersProps) => {
  const { t } = useTranslation();

  return (
    <Box>
      <MemoizedSearchFiltersForm>{children}</MemoizedSearchFiltersForm>
      <Box m={2}>
        <Grid container spacing={1}>
          <Grid item xs={12} display="flex" justifyContent="flex-end">
            <MediumButton
              startIcon={<FontAwesomeIcon style={{ fontSize: 12 }} icon={faSearch} />}
              size={"small"}
              disabled={isSubmitBtnDisabled}
              type={"submit"}
              variant="contained"
              color="primary"
            >
              {t("l.btn.search")}
            </MediumButton>
            <MediumGreyButton
              startIcon={<FontAwesomeIcon style={{ fontSize: 12 }} icon={faClose} />}
              size={"small"}
              disabled={isResetBtnDisabled || isSubmitBtnDisabled}
              onClick={handleResetFilters}
              style={{ marginLeft: "15px" }}
              variant="contained"
            >
              {t("l.btn.clear")}
            </MediumGreyButton>
          </Grid>
        </Grid>
      </Box>
    </Box>
  );
};
export default SearchFilters;
