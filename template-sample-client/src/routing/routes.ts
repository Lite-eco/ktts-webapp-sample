import { UserId } from '../generated/domain/Ids.generated';
import { Role } from '../generated/domain/User.generated';
import { dict, Dict, flatMap } from '../utils/nominal-class';
import { AccountView } from '../views/account/AccountView';
import { AdminManualCommandView } from '../views/admin/manual-command/AdminManualCommandView';
import { UsersManagementView } from '../views/admin/users-management/UsersManagementView';
import { LoginView } from '../views/login/LoginView';
import { RegisterView } from '../views/register/RegisterView';
import { RootView } from '../views/root/RootView';
import { FunctionComponent } from 'react';

// TODO[tmpl] secure that "name" can't be a route parameter
export type ApplicationRoute =
  | AccountRoute
  | AdminManualCommandRoute
  | LoginRoute
  | RegisterRoute
  | RootRoute
  | UsersManagementRoute
  | UsersManagementUserEditRolesRoute
  | UsersManagementUserRoute;

export interface ApplicationRouteProps<T extends ApplicationRoute> {
  name: ApplicationRoute['name'];
  path: string;
  component: FunctionComponent<{ route: T | undefined }>;
  rootSubComponent?: FunctionComponent<{ route: T | undefined }>;
  role?: Role;
  subRoutes?: ApplicationRouteProps<any>[];
}

// TODO naming
export const routes: ApplicationRouteProps<any>[] = [
  {
    name: 'AccountRoute',
    path: '/account',
    component: AccountView,
    role: 'User'
  },
  {
    name: 'AdminManualCommandRoute',
    path: '/admin/manual-command',
    component: AdminManualCommandView,
    role: 'Admin'
  },
  {
    name: 'LoginRoute',
    path: '/login',
    component: LoginView
  },
  {
    name: 'RegisterRoute',
    path: '/register',
    component: RegisterView
  },
  {
    name: 'RootRoute',
    path: '/',
    component: RootView
  },
  {
    name: 'UsersManagementRoute',
    path: '/users-management',
    component: UsersManagementView,
    role: 'Admin'
  },
  {
    name: 'UsersManagementUserEditRolesRoute',
    path: '/users-management/:userId/edit-roles',
    component: UsersManagementView,
    role: 'Admin'
  },
  {
    name: 'UsersManagementUserRoute',
    path: '/users-management/:userId',
    component: UsersManagementView,
    role: 'Admin'
  }
];

const flattenRoute = (
  rootPath: string,
  r: ApplicationRouteProps<any>
): [string, string][] => {
  const path = rootPath + r.path;
  return [
    [r.name, path],
    ...(r.subRoutes ? flatMap(r.subRoutes, r => flattenRoute(path, r)) : [])
  ];
};

export const routePathMap: Dict<ApplicationRoute['name'], string> = dict(
  flatMap(routes, r => flattenRoute('', r))
);

interface AccountRoute {
  name: 'AccountRoute';
}

interface AdminManualCommandRoute {
  name: 'AdminManualCommandRoute';
}

interface LoginRoute {
  name: 'LoginRoute';
}

interface RegisterRoute {
  name: 'RegisterRoute';
}

interface RootRoute {
  name: 'RootRoute';
}

export interface UsersManagementRoute {
  name: 'UsersManagementRoute';
}

export interface UsersManagementUserEditRolesRoute {
  name: 'UsersManagementUserEditRolesRoute';
  userId: UserId;
}

export interface UsersManagementUserRoute {
  name: 'UsersManagementUserRoute';
  userId: UserId;
}
