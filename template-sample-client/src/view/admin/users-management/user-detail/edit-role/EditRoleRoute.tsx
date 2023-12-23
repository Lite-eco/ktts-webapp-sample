/** @jsxImportSource @emotion/react */
import { AdminUsersManagementUserDetailEditRolePath } from 'generated/routing/ApplicationPath.generated';
import { useOutletContext } from 'react-router-dom';
import { useTypedParams } from 'utils/routing-utils';
import { UsersManagementUserDetailOutletContext } from 'view/admin/users-management/user-detail/UserDetailRoute';
import { UserEditRoleDialog } from 'view/admin/users-management/user-detail/edit-role/components/UserEditRoleDialog';

export const EditRoleRoute = () => {
  const params = useTypedParams<AdminUsersManagementUserDetailEditRolePath>();
  const outletContext =
    useOutletContext<UsersManagementUserDetailOutletContext>();
  return (
    <UserEditRoleDialog
      userId={params.userId}
      userInfos={outletContext.userInfos}
      updateUserInfos={outletContext.updateUserInfos}
      loadingUserInfos={outletContext.loadingUserInfos}
    />
  );
};
