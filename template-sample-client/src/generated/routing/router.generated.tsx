/** @jsxImportSource @emotion/react */
import { MainLayout } from 'view/components/layout/MainLayout';
import { AccountRoute } from 'view/account/AccountRoute';
import { AdminRootRoute } from 'view/admin/AdminRootRoute';
import { AdminRoute } from 'view/admin/AdminRoute';
import { ManualCommandRoute } from 'view/admin/manual-command/ManualCommandRoute';
import { RoutesListingRoute } from 'view/admin/routes-listing/RoutesListingRoute';
import { UsersManagementRoute } from 'view/admin/users-management/UsersManagementRoute';
import { UserDetailRoute } from 'view/admin/users-management/user-detail/UserDetailRoute';
import { EditRoleRoute } from 'view/admin/users-management/user-detail/edit-role/EditRoleRoute';
import { EditStatusRoute } from 'view/admin/users-management/user-detail/edit-status/EditStatusRoute';
import { LoginRoute } from 'view/login/LoginRoute';
import { NotFoundRoute } from 'view/not-found/NotFoundRoute';
import { RegisterRoute } from 'view/register/RegisterRoute';
import { RootRoute } from 'view/root/RootRoute';
import { LostPasswordRoute } from 'view/lost-password/LostPasswordRoute';
import { ResetLostPasswordRoute } from 'view/reset-lost-password/ResetLostPasswordRoute';
import { createBrowserRouter, RouteObject } from 'react-router-dom';

export const routes: RouteObject[] = [
  {
    id: 'MainLayout',
    element: <MainLayout />,
    children: [
      {
        id: 'Account',
        path: 'account',
        element: <AccountRoute />
      },
      {
        id: 'Admin',
        path: 'admin',
        element: <AdminRoute />,
        children: [
          { id: 'Admin/_', path: '', element: <AdminRootRoute /> },
          {
            id: 'Admin/ManualCommand',
            path: 'manual-command',
            element: <ManualCommandRoute />
          },
          {
            id: 'Admin/RouteListing',
            path: 'routes-listing',
            element: <RoutesListingRoute />
          },
          {
            id: 'Admin/UsersManagement',
            path: 'users-management',
            element: <UsersManagementRoute />,
            children: [
              {
                id: 'Admin/UsersManagement/UserDetail',
                path: ':userId',
                element: <UserDetailRoute />,
                children: [
                  {
                    id: 'Admin/UsersManagement/UserDetail/EditRole',
                    path: 'edit-role',
                    element: <EditRoleRoute />
                  },
                  {
                    id: 'Admin/UsersManagement/UserDetail/EditStatus',
                    path: 'edit-status',
                    element: <EditStatusRoute />
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
        element: <LoginRoute />
      },
      {
        id: 'LostPassword',
        path: 'lost-password',
        element: <LostPasswordRoute />
      },
      {
        id: 'Register',
        path: 'register',
        element: <RegisterRoute />
      },
      {
        id: 'ResetLostPassword',
        path: 'reset-lost-password',
        element: <ResetLostPasswordRoute />
      },
      {
        id: 'Root',
        path: '/',
        element: <RootRoute />
      },
      {
        id: 'NotFound',
        path: '*',
        element: <NotFoundRoute />
      }
    ]
  }
];
export const router = createBrowserRouter(routes);
