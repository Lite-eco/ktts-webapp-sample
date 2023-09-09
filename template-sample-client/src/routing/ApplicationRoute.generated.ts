import { UserId } from '../generated/domain/Ids.generated';

export type ApplicationRoute =
  | AccountRoute
  | AdminRoute
  | AdminManualCommandRoute
  | AdminUsersManagementRoute
  | AdminUsersManagementUserDetailRoute
  | AdminUsersManagementUserDetailEditRoleRoute
  | AdminUsersManagementUserDetailEditStatusRoute
  | LoginRoute
  | RegisterRoute
  | RootRoute;

interface AccountRoute {
  name: 'Account';
}

interface AdminRoute {
  name: 'Admin';
}

interface AdminManualCommandRoute {
  name: 'Admin/ManualCommand';
}

interface AdminUsersManagementRoute {
  name: 'Admin/UsersManagement';
}

export interface AdminUsersManagementUserDetailRoute {
  name: 'Admin/UsersManagement/UserDetail';
  userId: UserId;
}

export interface AdminUsersManagementUserDetailEditRoleRoute {
  name: 'Admin/UsersManagement/UserDetail/EditRole';
  userId: UserId;
}

export interface AdminUsersManagementUserDetailEditStatusRoute {
  name: 'Admin/UsersManagement/UserDetail/EditStatus';
  userId: UserId;
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
