import { createAxiosClient, keycloakInitObject, ProcessEnvironments } from "@duosoftbg/bpo-components";

export const axiosClient = createAxiosClient({
  baseUrl: `${ProcessEnvironments.Api.Admin.PatentClassification}/api/v1`,
  keycloak: keycloakInitObject,
});
