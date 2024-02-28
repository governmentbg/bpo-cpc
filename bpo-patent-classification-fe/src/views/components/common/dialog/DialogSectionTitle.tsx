import { Typography } from "@mui/material";
import { GridItem } from "@duosoftbg/bpo-components";
import { useTranslation } from "react-i18next";
import styled from "styled-components";

const SectionTitle = styled(Typography)`
  padding: 5px 5px 5px 10px;
  border-radius: 4px;
  font-size: 15px;
`;

const DialogSectionTitle = ({ label }: { label: string }) => {
  const { t } = useTranslation();

  return (
    <GridItem sm={12} md={12} lg={12}>
      <SectionTitle variant={"h6"} sx={{ backgroundColor: "#eee" }}>
        {t(label)}
      </SectionTitle>
    </GridItem>
  );
};

export default DialogSectionTitle;
