import * as React from "react";
import { Fragment } from "react";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableRow from "@mui/material/TableRow";
import { AlertSpg, OptionTableCell, TableButton } from "@duosoftbg/bpo-components";
import { useTranslation } from "react-i18next";
import BackofficeSearchTable from "../common/table/BackofficeSearchTable";
import { useFormContext } from "react-hook-form";

const headCells = [
  {
    id: "number",
    label: "l.table.head.number",
    sortable: false,
  },
  {
    id: "symbol",
    label: "l.table.head.symbol",
    sortable: false,
  },
  {
    id: "edition",
    label: "l.table.head.edition",
    sortable: false,
  },
  {
    id: "ipcName",
    label: "l.table.head.ipcName",
    sortable: false,
  },
  {
    id: "latestVersion",
    label: "l.table.head.latestVersion",
    sortable: false,
  },
  {
    id: "options",
    label: "",
    sortable: false,
  },
];

const OldVersionClassesListTable = ({ total, records, blockTable, onPageOrOrderChange, group }) => {
  const { t } = useTranslation();
  const { getValues } = useFormContext();

  return (
    <Fragment>
      {total > 0 && (
        <BackofficeSearchTable
          group={group}
          headCells={headCells}
          total={total}
          blockTable={blockTable}
          onPageOrOrderChange={onPageOrOrderChange}
        >
          <TableBody>
            {records.map((row, index) => (
              <TableRow hover key={row.symbol}>
                <TableCell>{index + 1 + getValues().page * getValues().pageSize}</TableCell>
                <TableCell>{row.symbol}</TableCell>
                <TableCell>{row.edition}</TableCell>
                <TableCell>{row.ipcName}</TableCell>
                <TableCell>{row.ipcLatestVersion}</TableCell>
                <OptionTableCell>
                  <TableButton
                    type={"view"}
                    to={`/classifier/old-class-versions/view/${row.symbol.replace("/", "-")}/${row.edition}`}
                  />
                </OptionTableCell>
              </TableRow>
            ))}
          </TableBody>
        </BackofficeSearchTable>
      )}
      {!(total > 0) && (
        <AlertSpg mt={10} mb={10} severity="info">
          {t("m.empty.list")}
        </AlertSpg>
      )}
    </Fragment>
  );
};

export default OldVersionClassesListTable;
