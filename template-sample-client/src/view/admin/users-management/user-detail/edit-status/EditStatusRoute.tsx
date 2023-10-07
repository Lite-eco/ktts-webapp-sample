/** @jsxImportSource @emotion/react */
import { AdminUsersManagementUserDetailEditStatusPath } from '../../../../../generated/routing/ApplicationPath.generated';
import { useTypedParams } from '../../../../../utils/routing-utils';
import { UsersManagementOutletContext } from '../../UsersManagementRoute';
import { UserEditStatusDialog } from './components/UserEditStatusDialog';
import { useOutletContext } from 'react-router-dom';

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
