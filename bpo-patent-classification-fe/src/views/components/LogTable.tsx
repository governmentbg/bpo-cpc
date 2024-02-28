import { Table, TableBody, TableContainer, TableHead } from "@mui/material";
import { AppSectionTitle, DATE_TIME_FORMAT, GridItem } from "@duosoftbg/bpo-components";
import { useTranslation } from "react-i18next";
import React from "react";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import { format } from "date-fns";

const LogTable = ({ processHistory }) => {
  const { t } = useTranslation();

  if (processHistory === null || processHistory.length === 0) {
    return null;
  }

  return (
    <GridItem sm={12} md={12}>
      <AppSectionTitle title={"l.operations.history"} />
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell style={{ width: "3%" }}>№</TableCell>
              <TableCell>{t("l.operation")}</TableCell>
              <TableCell>{t("l.time.started")}</TableCell>
              <TableCell>{t("l.time.finished")}</TableCell>
              <TableCell>{t("l.user")}</TableCell>
              <TableCell>{t("l.status")}</TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {processHistory.map((row, index) => (
              <TableRow hover key={"row-" + index}>
                <TableCell>{index + 1}</TableCell>
                <TableCell>{t(row.processName)}</TableCell>
                <TableCell>{format(new Date(row.startTime), DATE_TIME_FORMAT)}</TableCell>
                <TableCell>{row.endTime ? format(new Date(row.endTime), DATE_TIME_FORMAT) : ""}</TableCell>
                <TableCell>{row.user}</TableCell>
                <TableCell>{t(row.status)}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </GridItem>
  );
};

export default LogTable;
