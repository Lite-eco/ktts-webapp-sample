import { UserId } from '../generated/domain/Ids.generated';
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

// eslint-disable-next-line @typescript-eslint/no-unused-vars
type routesDsl = {
  Account: {
    path: 'account';
    container: typeof MainViewContainer;
    component: typeof AccountView;
  };
  Admin: {
    path: 'admin';
    container: typeof MainViewContainer;
    component: typeof AdminView;
    subRoutes: {
      ManualCommand: {
        path: 'manual-command';
        component: typeof ManualCommandView;
      };
      RouteListing: {
        path: 'routes-listing';
        component: typeof RoutesListingView;
      };
      UsersManagement: {
        path: 'users-management';
        component: typeof UsersManagementView;
        subRoutes: {
          UserDetail: {
            path: ':userId';
            component: typeof UserDetailView;
            params: {
              userId: UserId;
            };
            subRoutes: {
              EditRole: {
                path: 'edit-role';
                component: typeof EditRoleView;
              };
              EditStatus: {
                path: 'edit-status';
                component: typeof EditStatusView;
              };
            };
          };
        };
      };
    };
  };
  Login: {
    path: 'login';
    container: typeof MainViewContainer;
    component: typeof LoginView;
  };
  Register: {
    path: 'register';
    container: typeof MainViewContainer;
    component: typeof RegisterView;
  };
  Root: {
    path: '';
    container: typeof MainViewContainer;
    component: typeof RootView;
  };
  NotFound: {
    path: '*';
    container: typeof MainViewContainer;
    component: typeof NotFoundView;
  };
};
