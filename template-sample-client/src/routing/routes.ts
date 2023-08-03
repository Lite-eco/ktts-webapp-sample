import { FunctionComponent } from 'react';
import { Role } from '../domain/user';
import { LoginView } from '../view/LoginView';
import { RegisterView } from '../view/RegisterView';
import { RootView } from '../view/RootView';
import { UsersManagementView } from '../view/UsersManagementView';
import { UserId } from '../domain/ids';
import { useParams } from 'react-router-dom';
import { UserManagementView } from '../view/UserManagementView';

export type Route =
  | LoginRoute
  | RegisterRoute
  | RootRoute
  | UserManagementRoute
  | UsersManagementRoute;

export const useRouteParams = <T extends Route>() =>
  useParams() as unknown as Omit<T, 'name'>;

export interface ApplicationRouteProps {
  path: string;
  component: FunctionComponent;
  role?: Role;
}

export const routes: Record<Route['name'], ApplicationRouteProps> = {
  LoginRoute: {
    path: '/login',
    component: LoginView
  },
  RegisterRoute: {
    path: '/register',
    component: RegisterView
  },
  RootRoute: {
    path: '/',
    component: RootView
  },
  UserManagementRoute: {
    path: '/users-management/:userId',
    component: UserManagementView,
    role: 'admin'
  },
  UsersManagementRoute: {
    path: '/users-management',
    component: UsersManagementView,
    role: 'admin'
  }
};

interface LoginRoute {
  name: 'LoginRoute';
}

interface RegisterRoute {
  name: 'RegisterRoute';
}

interface RootRoute {
  name: 'RootRoute';
}

export interface UserManagementRoute {
  name: 'UserManagementRoute';
  userId: UserId;
}

interface UsersManagementRoute {
  name: 'UsersManagementRoute';
}
