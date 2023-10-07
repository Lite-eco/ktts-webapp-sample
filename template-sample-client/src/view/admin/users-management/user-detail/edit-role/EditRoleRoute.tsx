/** @jsxImportSource @emotion/react */
import { AdminUsersManagementUserDetailEditRolePath } from '../../../../../generated/routing/ApplicationPath.generated';
import { useTypedParams } from '../../../../../utils/routing-utils';
import { UsersManagementUserDetailOutletContext } from '../UserDetailRoute';
import { UserEditRoleDialog } from './components/UserEditRoleDialog';
import { useOutletContext } from 'react-router-dom';

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
