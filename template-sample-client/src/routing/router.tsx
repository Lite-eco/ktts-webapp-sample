/** @jsxImportSource @emotion/react */
import { AccountView } from '../views/account/AccountView';
import { AdminManualCommandView } from '../views/admin/manual-command/AdminManualCommandView';
import { UsersManagementView } from '../views/admin/users-management/UsersManagementView';
import { UserDetailView } from '../views/admin/users-management/user-detail/UserDetailView';
import { EditRolesView } from '../views/admin/users-management/user-detail/edit-roles/EditRolesView';
import { LoginView } from '../views/login/LoginView';
import { NotFoundView } from '../views/not-found/NotFoundView';
import { RegisterView } from '../views/register/RegisterView';
import { RootView } from '../views/root/RootView';
import { createBrowserRouter } from 'react-router-dom';

export const router = createBrowserRouter([
  {
    id: 'Account',
    path: 'account',
    element: <AccountView />
  },
  {
    id: 'Admin',
    path: 'admin/manual-command',
    element: <AdminManualCommandView />
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
    id: 'UsersManagement',
    path: 'users-management',
    element: <UsersManagementView />,
    children: [
      {
        id: 'UsersManagement/UserDetail',
        path: ':userId',
        element: <UserDetailView />,
        children: [
          {
            id: 'UsersManagement/UserDetail/EditRoles',
            path: 'edit-roles',
            element: <EditRolesView />
          }
        ]
      }
    ]
  },
  {
    id: 'NotFound',
    path: '*',
    element: <NotFoundView />
  }
]);
