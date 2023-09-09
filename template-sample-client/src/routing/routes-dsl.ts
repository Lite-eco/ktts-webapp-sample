import { UserId } from '../generated/domain/Ids.generated';
import { AccountView } from '../views/account/AccountView';
import { AdminView } from '../views/admin/AdminView';
import { ManualCommandView } from '../views/admin/manual-command/ManualCommandView';
import { UsersManagementView } from '../views/admin/users-management/UsersManagementView';
import { UserDetailView } from '../views/admin/users-management/user-detail/UserDetailView';
import { EditRoleView } from '../views/admin/users-management/user-detail/edit-role/EditRoleView';
import { EditStatusView } from '../views/admin/users-management/user-detail/edit-status/EditStatusView';
import { LoginView } from '../views/login/LoginView';
import { NotFoundView } from '../views/not-found/NotFoundView';
import { RegisterView } from '../views/register/RegisterView';
import { RootView } from '../views/root/RootView';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
type routesDsl = [
  {
    name: 'Account';
    path: 'account';
    component: typeof AccountView;
  },
  {
    name: 'Admin';
    path: 'admin';
    component: typeof AdminView;
    subRoutes: [
      {
        name: 'ManualCommand';
        path: 'manual-command';
        component: typeof ManualCommandView;
      },
      {
        name: 'UsersManagement';
        path: 'users-management';
        component: typeof UsersManagementView;
        subRoutes: [
          {
            name: 'UserDetail';
            path: ':userId';
            component: typeof UserDetailView;
            params: {
              userId: UserId;
            };
            subRoutes: [
              {
                name: 'EditRole';
                path: 'edit-role';
                component: typeof EditRoleView;
              },
              {
                name: 'EditStatus';
                path: 'edit-status';
                component: typeof EditStatusView;
              }
            ];
          }
        ];
      }
    ];
  },
  {
    name: 'Login';
    path: 'login';
    component: typeof LoginView;
  },
  {
    name: 'Register';
    path: 'register';
    component: typeof RegisterView;
  },
  {
    name: 'Root';
    path: '';
    component: typeof RootView;
  },
  {
    name: 'NotFound';
    path: '*';
    component: typeof NotFoundView;
  }
];
