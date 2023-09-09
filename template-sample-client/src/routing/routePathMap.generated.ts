import { dict } from '../utils/nominal-class';
import { ApplicationRoute } from './ApplicationRoute.generated';

export const routePathMap = dict<ApplicationRoute['name'], string>([
  ['Account', '/account'],
  ['Admin', '/admin'],
  ['Admin/ManualCommand', '/admin/manual-command'],
  ['Admin/RouteListing', '/admin/routes-listing'],
  ['Admin/UsersManagement', '/admin/users-management'],
  ['Admin/UsersManagement/UserDetail', '/admin/users-management/:userId'],
  [
    'Admin/UsersManagement/UserDetail/EditRole',
    '/admin/users-management/:userId/edit-role'
  ],
  [
    'Admin/UsersManagement/UserDetail/EditStatus',
    '/admin/users-management/:userId/edit-status'
  ],
  ['Login', '/login'],
  ['Register', '/register'],
  ['Root', '/']
]);
