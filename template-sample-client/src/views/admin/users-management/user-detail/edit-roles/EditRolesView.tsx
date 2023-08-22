/** @jsxImportSource @emotion/react */
import { UsersManagementUserDetailRoute } from '../../../../../routing/ApplicationRoute';
import { useTypedParams } from '../../../../../routing/routing-utils';
import { UsersManagementUserDetailOutletContext } from '../UserDetailView';
import { UserEditRolesDialog } from './components/UserEditRolesDialog';
import { useOutletContext } from 'react-router-dom';

export const EditRolesView = () => {
  const params = useTypedParams<UsersManagementUserDetailRoute>();
  const outletContext =
    useOutletContext<UsersManagementUserDetailOutletContext>();
  return (
    <UserEditRolesDialog
      userId={params.userId}
      userInfos={outletContext.userInfos}
      updateUserInfos={outletContext.updateUserInfos}
      loadingUserInfos={outletContext.loadingUserInfos}
    />
  );
};
