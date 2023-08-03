import { NominalString } from '../utils/nominal-class';

export type TemplateSampleId =
  | CommandLogId
  | DeploymentLogId
  | MailLogId
  | TemplateSampleStringId
  | TemplateSampleUuidId
  | RequestErrorId
  | UserFileId
  | UserId
  | UserSessionId;

export type CommandLogId = NominalString<'CommandLogId'>;
export type DeploymentLogId = NominalString<'DeploymentLogId'>;
export type MailLogId = NominalString<'MailLogId'>;
export type TemplateSampleStringId = NominalString<'TemplateSampleStringId'>;
export type TemplateSampleUuidId = NominalString<'TemplateSampleUuidId'>;
export type RequestErrorId = NominalString<'RequestErrorId'>;
export type UserFileId = NominalString<'UserFileId'>;
export type UserId = NominalString<'UserId'>;
export type UserSessionId = NominalString<'UserSessionId'>;
