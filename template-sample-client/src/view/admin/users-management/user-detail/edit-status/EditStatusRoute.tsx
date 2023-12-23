/** @jsxImportSource @emotion/react */
import { AdminUsersManagementUserDetailEditStatusPath } from 'generated/routing/ApplicationPath.generated';
import { useOutletContext } from 'react-router-dom';
import { useTypedParams } from 'utils/routing-utils';
import { UsersManagementOutletContext } from 'view/admin/users-management/UsersManagementRoute';
import { UserEditStatusDialog } from 'view/admin/users-management/user-detail/edit-status/components/UserEditStatusDialog';

export const EditStatusRoute = () => {
  const params = useTypedParams<AdminUsersManagementUserDetailEditStatusPath>();
  const outletContext = useOutletContext<UsersManagementOutletContext>();
  return (
    <UserEditStatusDialog
      userId={params.userId}
      updateUserInfos={outletContext.updateUserInfos}
    />
  );
};
