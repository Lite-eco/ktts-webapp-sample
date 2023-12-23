import { ApplicationEnvironment } from 'generated/domain/Application.generated';
import { UserInfos } from 'generated/domain/User.generated';

export interface ApplicationBootstrapData {
  env: ApplicationEnvironment;
  userInfos?: UserInfos;
}
