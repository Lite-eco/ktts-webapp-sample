/** @jsxImportSource @emotion/react */
import { AccountView } from '../views/account/AccountView';
import { AdminView } from '../views/admin/AdminView';
import { ManualCommandView } from '../views/admin/manual-command/ManualCommandView';
import { RoutesListingView } from '../views/admin/routes-listing/RoutesListingView';
import { UsersManagementView } from '../views/admin/users-management/UsersManagementView';
import { UserDetailView } from '../views/admin/users-management/user-detail/UserDetailView';
import { EditRoleView } from '../views/admin/users-management/user-detail/edit-role/EditRoleView';
import { EditStatusView } from '../views/admin/users-management/user-detail/edit-status/EditStatusView';
import { MainViewContainer } from '../views/containers/MainViewContainer';
import { LoginView } from '../views/login/LoginView';
import { NotFoundView } from '../views/not-found/NotFoundView';
import { RegisterView } from '../views/register/RegisterView';
import { RootView } from '../views/root/RootView';
import { RouteObject } from 'react-router-dom';

export const routes: RouteObject[] = [
  {
    id: 'MainViewContainer',
    element: <MainViewContainer />,
    children: [
      {
        id: 'Account',
        path: 'account',
        element: <AccountView />
      },
      {
        id: 'Admin',
        path: 'admin',
        element: <AdminView />,
        children: [
          {
            id: 'Admin/ManualCommand',
            path: 'manual-command',
            element: <ManualCommandView />
          },
          {
            id: 'Admin/RouteListing',
            path: 'routes-listing',
            element: <RoutesListingView />
          },
          {
            id: 'Admin/UsersManagement',
            path: 'users-management',
            element: <UsersManagementView />,
            children: [
              {
                id: 'Admin/UsersManagement/UserDetail',
                path: ':userId',
                element: <UserDetailView />,
                children: [
                  {
                    id: 'Admin/UsersManagement/UserDetail/EditRole',
                    path: 'edit-role',
                    element: <EditRoleView />
                  },
                  {
                    id: 'Admin/UsersManagement/UserDetail/EditStatus',
                    path: 'edit-status',
                    element: <EditStatusView />
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        id: 'Login',
        path: 'login',
        element: <LoginView />
      },
      {
        id: 'Register',
        path: 'register',
        element: <RegisterView />
      },
      {
        id: 'Root',
        path: '/',
        element: <RootView />
      },
      {
        id: 'NotFound',
        path: '*',
        element: <NotFoundView />
      }
    ]
  }
];
