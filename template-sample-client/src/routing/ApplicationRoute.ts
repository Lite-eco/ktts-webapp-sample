import { UserId } from '../generated/domain/Ids.generated';

export type ApplicationRoute =
  | AccountRoute
  | AdminRoute
  | AdminManualCommandRoute
  | AdminUsersManagementRoute
  | AdminUsersManagementUserDetailRoute
  | AdminUsersManagementUserDetailEditRolesRoute
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

export interface AdminUsersManagementUserDetailEditRolesRoute {
  name: 'Admin/UsersManagement/UserDetail/EditRoles';
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
