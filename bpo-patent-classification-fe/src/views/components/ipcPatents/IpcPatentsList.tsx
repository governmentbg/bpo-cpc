import React, { Fragment } from "react";
import { Box, Grid } from "@mui/material";
import { GridSpg, TableSkeleton } from "@duosoftbg/bpo-components";
import IpcPatentsListTable from "./IpcPatentsListTable";

const IpcPatentsList = ({ records, total, isLoading, onPageOrOrderChange, blockTable, group, showCheckboxes }) => {
  const showTable = () => {
    return (
      <Fragment>
        <IpcPatentsListTable
          records={records}
          total={total}
          onPageOrOrderChange={onPageOrOrderChange}
          blockTable={blockTable}
          group={group}
          showCheckboxes={showCheckboxes}
        />
      </Fragment>
    );
  };

  return (
    <Box>
      <Grid container spacing={1}>
        <GridSpg item xs={12}>
          {isLoading ? <TableSkeleton /> : showTable()}
        </GridSpg>
      </Grid>
    </Box>
  );
};

export default IpcPatentsList;
