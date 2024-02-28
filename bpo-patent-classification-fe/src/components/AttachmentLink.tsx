import styled from "styled-components";

const AttachmentLink = styled.a`
  text-decoration: none;
  color: ${(props) => props.theme.palette.primary.light};
  cursor: pointer;

  :hover {
    text-decoration: underline;
  }
`;
export default AttachmentLink;
