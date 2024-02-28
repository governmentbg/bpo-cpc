import * as React from "react";

import {
  ASC_ORDER,
  BpoTable,
  DEFAULT_PAGE,
  DESC_ORDER,
  InvisibleBackdrop,
  RelativeBox,
} from "@duosoftbg/bpo-components";
import { useFormContext } from "react-hook-form";

const BackofficeSearchTable = ({ group, total, headCells, blockTable, onPageOrOrderChange, ...others }) => {
  const { setValue, getValues } = useFormContext();

  const handleRequestSort = (event, property) => {
    const isAsc = getValues().orderBy === property && getValues().order === ASC_ORDER;
    setValue("order", isAsc ? DESC_ORDER : ASC_ORDER);
    setValue("orderBy", property);
    onPageOrOrderChange();
  };

  const handleChangePage = (event, newPage) => {
    setValue("page", newPage);
    onPageOrOrderChange();
  };

  const handleChangeRowsPerPage = (event) => {
    setValue("page", DEFAULT_PAGE);
    setValue("pageSize", parseInt(event.target.value, 10));
    onPageOrOrderChange();
  };

  return (
    <RelativeBox>
      <InvisibleBackdrop open={blockTable} />
      <BpoTable
        headCells={headCells}
        isSmall={true}
        total={total}
        page={getValues().page}
        pageSize={getValues().pageSize}
        order={getValues().order}
        orderBy={getValues().orderBy}
        onPageChange={handleChangePage}
        onSortClick={handleRequestSort}
        onRowsPerPageChange={handleChangeRowsPerPage}
      >
        {others.children}
      </BpoTable>
    </RelativeBox>
  );
};

export default BackofficeSearchTable;
