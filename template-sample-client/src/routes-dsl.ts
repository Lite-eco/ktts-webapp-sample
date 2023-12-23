import { MainLayout } from 'common-components/layout/MainLayout';
import { UserId } from 'generated/domain/Ids.generated';
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

// eslint-disable-next-line @typescript-eslint/no-unused-vars
type routesDsl = {
  Account: {
    path: 'account';
    layout: typeof MainLayout;
    component: typeof AccountRoute;
  };
  Admin: {
    path: 'admin';
    layout: typeof MainLayout;
    component: typeof AdminRoute;
    rootComponent: typeof AdminRootRoute;
    subRoutes: {
      ManualCommand: {
        path: 'manual-command';
        component: typeof ManualCommandRoute;
      };
      RouteListing: {
        path: 'routes-listing';
        component: typeof RoutesListingRoute;
      };
      UsersManagement: {
        path: 'users-management';
        component: typeof UsersManagementRoute;
        subRoutes: {
          UserDetail: {
            path: ':userId';
            component: typeof UserDetailRoute;
            params: {
              userId: UserId;
            };
            subRoutes: {
              EditRole: {
                path: 'edit-role';
                component: typeof EditRoleRoute;
              };
              EditStatus: {
                path: 'edit-status';
                component: typeof EditStatusRoute;
              };
            };
          };
        };
      };
    };
  };
  Login: {
    path: 'login';
    layout: typeof MainLayout;
    component: typeof LoginRoute;
  };
  Register: {
    path: 'register';
    layout: typeof MainLayout;
    component: typeof RegisterRoute;
  };
  Root: {
    path: '';
    layout: typeof MainLayout;
    component: typeof RootRoute;
  };
  NotFound: {
    path: '*';
    layout: typeof MainLayout;
    component: typeof NotFoundRoute;
  };
};
