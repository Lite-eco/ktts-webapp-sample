import { UserId } from 'generated/domain/Ids.generated';

export type ApplicationPath =
  | AccountPath
  | AdminPath
  | AdminManualCommandPath
  | AdminRouteListingPath
  | AdminUsersManagementPath
  | AdminUsersManagementUserDetailPath
  | AdminUsersManagementUserDetailEditRolePath
  | AdminUsersManagementUserDetailEditStatusPath
  | LoginPath
  | LostPasswordPath
  | RegisterPath
  | ResetLostPasswordPath
  | RootPath;

interface AccountPath {
  name: 'Account';
}

interface AdminPath {
  name: 'Admin';
}

interface AdminManualCommandPath {
  name: 'Admin/ManualCommand';
}

interface AdminRouteListingPath {
  name: 'Admin/RouteListing';
}

interface AdminUsersManagementPath {
  name: 'Admin/UsersManagement';
}

export interface AdminUsersManagementUserDetailPath {
  name: 'Admin/UsersManagement/UserDetail';
  userId: UserId;
}

export interface AdminUsersManagementUserDetailEditRolePath {
  name: 'Admin/UsersManagement/UserDetail/EditRole';
  userId: UserId;
}

export interface AdminUsersManagementUserDetailEditStatusPath {
  name: 'Admin/UsersManagement/UserDetail/EditStatus';
  userId: UserId;
}

interface LoginPath {
  name: 'Login';
}

interface LostPasswordPath {
  name: 'LostPassword';
}

interface RegisterPath {
  name: 'Register';
}

interface ResetLostPasswordPath {
  name: 'ResetLostPassword';
}

interface RootPath {
  name: 'Root';
}
