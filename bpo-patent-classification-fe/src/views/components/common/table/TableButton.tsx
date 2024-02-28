import { Link } from "react-router-dom";
import { IconButton } from "@mui/material";
import * as React from "react";
import { Fragment } from "react";
import {
  faAdd,
  faDeleteLeft,
  faEdit,
  faEye,
  faPaperPlane,
  faRemove,
  faSearch,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import styled from "styled-components/macro";

type TableViewButtonProps = {
  to?: string;
  type?: ButtonType;
  onClick?: any;
};

type ButtonType = "view" | "edit" | "delete" | "remove" | "create" | "resend" | "more";

const ViewButton = styled(IconButton)<{ component?: any }>`
  border: 1px solid #ccc;
  border-radius: 3px;
  color: #333;
  margin-left: 2px;
  margin-right: 2px;
`;

const TableButton = ({ to, type = "view", onClick }: TableViewButtonProps) => {
  const renderIcon = (type: ButtonType) => {
    switch (type) {
      case "view":
        return faSearch;
      case "edit":
        return faEdit;
      case "delete":
        return faDeleteLeft;
      case "remove":
        return faRemove;
      case "create":
        return faAdd;
      case "resend":
        return faPaperPlane;
      case "more":
        return faEye;
      default:
        return null;
    }
  };
  return (
    <Fragment>
      {to && (
        <Link to={to}>
          <ViewButton sx={{ color: "primary.lightBlue" }} component="span">
            <FontAwesomeIcon style={{ fontSize: 12 }} icon={renderIcon(type)} />
          </ViewButton>
        </Link>
      )}
      {!to && (
        <ViewButton onClick={() => onClick()} sx={{ color: "primary.lightBlue" }} component="span">
          <FontAwesomeIcon style={{ fontSize: 12 }} icon={renderIcon(type)} />
        </ViewButton>
      )}
    </Fragment>
  );
};

export default TableButton;
