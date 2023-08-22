import { dict } from '../utils/nominal-class';
import { ApplicationRoute } from './ApplicationRoute.generated';

export const routePathMap = dict<ApplicationRoute['name'], string>([
  ['Account', '/account'],
  ['Admin', '/admin'],
  ['Admin/ManualCommand', '/admin/manual-command'],
  ['Admin/UsersManagement', '/admin/users-management'],
  ['Admin/UsersManagement/UserDetail', '/admin/users-management/:userId'],
  [
    'Admin/UsersManagement/UserDetail/EditRoles',
    '/admin/users-management/:userId/edit-roles'
  ],
  ['Login', '/login'],
  ['Register', '/register'],
  ['Root', '/']
]);
