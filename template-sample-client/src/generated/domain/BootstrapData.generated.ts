import { ApplicationEnvironment } from './Application.generated';
import { UserInfos } from './User.generated';

export interface ApplicationBootstrapData {
  env: ApplicationEnvironment;
  userInfos?: UserInfos;
}
