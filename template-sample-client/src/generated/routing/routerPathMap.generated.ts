import { pairsToDict } from 'utils/nominal-class';
import { ApplicationPath } from 'generated/routing/ApplicationPath.generated';

export const routerPathMap = pairsToDict<ApplicationPath['name'], string>([
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
  ['LostPassword', '/lost-password'],
  ['Register', '/register'],
  ['ResetLostPassword', '/reset-lost-password'],
  ['Root', '/']
]);
