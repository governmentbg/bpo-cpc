import * as React from "react";
import { Fragment } from "react";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableRow from "@mui/material/TableRow";
import { AlertSpg, OptionTableCell, TableButton, useCheckboxIdsControl } from "@duosoftbg/bpo-components";
import { useTranslation } from "react-i18next";
import BackofficeSearchTable from "../common/table/BackofficeSearchTable";
import { useFormContext } from "react-hook-form";
import { Checkbox } from "@mui/material";

const headCells = [
  {
    id: "number",
    label: "l.table.head.number",
    sortable: false,
  },
  {
    id: "entryNum",
    label: "l.table.head.entryNum",
    sortable: false,
  },
  {
    id: "regNum",
    label: "l.table.head.regNum",
    sortable: false,
  },
  {
    id: "patentTitle",
    label: "l.table.head.patentTitle",
    sortable: false,
  },
  {
    id: "options",
    label: "",
    sortable: false,
  },
];

const IpcPatentsListTable = ({ total, records, blockTable, onPageOrOrderChange, group, showCheckboxes }) => {
  const { t } = useTranslation();
  const { getValues } = useFormContext();

  const { handleSingleIdToggle, handleMultipleIdsToggle, isSingleAddChecked, isMultipleAddChecked } =
    useCheckboxIdsControl({
      records: records,
    });

  let headCellsUpdated = [...headCells];
  if (showCheckboxes) {
    headCellsUpdated.splice(1, 0, {
      id: "globalCheckboxSelect",
      label: "",
      // @ts-ignore
      component: <Checkbox checked={isMultipleAddChecked()} onClick={handleMultipleIdsToggle} />,
    });
  }

  return (
    <Fragment>
      {total > 0 && (
        <BackofficeSearchTable
          group={group}
          headCells={headCellsUpdated}
          total={total}
          blockTable={blockTable}
          onPageOrOrderChange={onPageOrOrderChange}
        >
          <TableBody>
            {records.map((row, index) => (
              <TableRow hover key={"row-" + index}>
                <TableCell>{index + 1 + getValues().page * getValues().pageSize}</TableCell>
                {showCheckboxes && (
                  <TableCell>
                    <Checkbox
                      checked={isSingleAddChecked(row.id)}
                      onClick={(event) => {
                        handleSingleIdToggle(event, row.id);
                      }}
                    />
                  </TableCell>
                )}
                <TableCell>{row.id}</TableCell>
                <TableCell>{row.regNum}</TableCell>
                <TableCell>{row.patentTitle}</TableCell>
                <OptionTableCell>
                  <TableButton
                    type={"view"}
                    externalLink
                    target={"_blank"}
                    to={`${process.env.REACT_APP_IPAS_URL}${row.urlType}/detail/${row.id}`}
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

export default IpcPatentsListTable;
