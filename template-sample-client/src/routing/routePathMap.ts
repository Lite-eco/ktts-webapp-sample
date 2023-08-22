import { dict } from '../utils/nominal-class';
import { ApplicationRoute } from './ApplicationRoute';

export const routePathMap = dict<ApplicationRoute['name'], string>([
  ['Account', '/account'],
  ['AdminManualCommand', '/admin/manual-command'],
  ['Login', '/login'],
  ['Register', '/register'],
  ['Root', '/'],
  ['UsersManagement', '/users-management'],
  ['UsersManagement/UserDetail', '/users-management/:userId'],
  [
    'UsersManagement/UserDetail/EditRoles',
    '/users-management/:userId/edit-roles'
  ]
]);
