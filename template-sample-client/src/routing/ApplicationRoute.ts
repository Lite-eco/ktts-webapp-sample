import { UserId } from '../generated/domain/Ids.generated';

export type ApplicationRoute =
  | AccountRoute
  | AdminManualCommandRoute
  | LoginRoute
  | RegisterRoute
  | RootRoute
  | UsersManagementRoute
  | UsersManagementUserDetailRoute
  | UsersManagementUserDetailEditRolesRoute;

interface AccountRoute {
  name: 'Account';
}

interface AdminManualCommandRoute {
  name: 'AdminManualCommand';
}

interface LoginRoute {
  name: 'Login';
}

interface RegisterRoute {
  name: 'Register';
}

interface RootRoute {
  name: 'Root';
}

interface UsersManagementRoute {
  name: 'UsersManagement';
}

export interface UsersManagementUserDetailRoute {
  name: 'UsersManagement/UserDetail';
  userId: UserId;
}

export interface UsersManagementUserDetailEditRolesRoute {
  name: 'UsersManagement/UserDetail/EditRoles';
  userId: UserId;
}
