/** @jsxImportSource @emotion/react */
import { AdminUsersManagementUserDetailEditStatusRoute } from '../../../../../routing/ApplicationRoute.generated';
import { useTypedParams } from '../../../../../routing/routing-utils';
import { UsersManagementOutletContext } from '../../UsersManagementView';
import { UserEditStatusDialog } from './components/UserEditStatusDialog';
import { useOutletContext } from 'react-router-dom';

export const EditStatusView = () => {
  const params =
    useTypedParams<AdminUsersManagementUserDetailEditStatusRoute>();
  const outletContext = useOutletContext<UsersManagementOutletContext>();
  return (
    <UserEditStatusDialog
      userId={params.userId}
      updateUserInfos={outletContext.updateUserInfos}
    />
  );
};
