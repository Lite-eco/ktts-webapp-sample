/** @jsxImportSource @emotion/react */
import { AdminUsersManagementUserDetailEditRoleRoute } from '../../../../../routing/ApplicationRoute.generated';
import { useTypedParams } from '../../../../../routing/routing-utils';
import { UsersManagementUserDetailOutletContext } from '../UserDetailView';
import { UserEditRoleDialog } from './components/UserEditRoleDialog';
import { useOutletContext } from 'react-router-dom';

export const EditRoleView = () => {
  const params = useTypedParams<AdminUsersManagementUserDetailEditRoleRoute>();
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
