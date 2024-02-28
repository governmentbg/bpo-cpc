#!/usr/bin/node
const { ExternalConfigManager } = require("@duosoftbg/bpo-frontend-config-manager/dist/cjs/index");

const profile = process.argv[2];
console.log(`Active profile: ${profile}`);

let configServerUrl = "....";
if (profile && profile === "stage") {
  configServerUrl = "....";
}
if (profile && profile === "production") {
  configServerUrl = "...";
}

ExternalConfigManager.run({
  configServerUrl: configServerUrl,
  configName: "bpo-patent-classification-fe",
  profile: profile,
  allProfiles: ["dev", "office", "stage", "production"],
});
