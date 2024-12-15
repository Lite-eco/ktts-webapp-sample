/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import {
  Autocomplete,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField
} from '@mui/material';
import { LoadingStatusButton } from 'view/components/LoadingButton';
import { statusEnumValues } from 'domain/enums';
import { Errors } from 'errors';
import { UserId } from 'generated/domain/Ids.generated';
import { AdminUserInfos, UserStatus } from 'generated/domain/User.generated';
import { AdminGetUserInfosQueryResponse } from 'generated/query/Queries.generated';
import { useI18n } from 'hooks/i18n';
import { LoadingStatus } from 'interfaces';
import { useEffect, useState } from 'react';
import { appContext } from 'services/ApplicationContext';
import { useUserState } from 'state/UserState';
import { navigateTo } from 'utils/routing-utils';
import { UserEditStatusDialogI18n } from 'view/admin/users-management/user-detail/edit-status/components/UserEditStatusDialog.i18n';

export const UserEditStatusDialog = (props: {
  userId: UserId;
  updateUserInfos: (user: AdminUserInfos) => void;
}) => {
  const [userInfos, setUserInfos] = useState<AdminUserInfos>();
  const [status, setStatus] = useState<UserStatus>();
  const [queryLoading, setQueryLoading] = useState<LoadingStatus>('Idle');
  const [updateLoading, setUpdateLoading] = useState<LoadingStatus>('Idle');
  const loggedInUserInfos = useUserState(s => s.userInfos);
  if (!loggedInUserInfos) {
    throw Errors._21bea505();
  }
  useEffect(() => {
    if (props.userId) {
      // keep after if (props.userId) for dialog disappearing animation
      setUserInfos(undefined);
      setStatus(undefined);
      setQueryLoading('Loading');
      appContext.queryService
        .send<AdminGetUserInfosQueryResponse>({
          objectType: 'AdminGetUserInfosQuery',
          userId: props.userId
        })
        .then(r => {
          setQueryLoading('Idle');
          setUserInfos(r.userInfos);
          setStatus(r.userInfos?.status);
        });
    }
  }, [props.userId]);
  const close = () => {
    if (props.userId) {
      navigateTo({
        name: 'Admin/UsersManagement/UserDetail',
        userId: props.userId
      });
    }
  };
  const save = () => {
    if (!userInfos) {
      throw Errors._7914a749();
    }
    if (!props.userId) {
      throw Errors._3b84f677();
    }
    if (status && status !== userInfos.status) {
      setUpdateLoading('Loading');
      appContext.commandService
        .send({
          objectType: 'AdminUpdateStatusCommand',
          userId: props.userId,
          status
        })
        .then(() => {
          setUpdateLoading('Idle');
          props.updateUserInfos({ ...userInfos, status });
          close();
        });
    } else {
      close();
    }
  };
  const t = useI18n(UserEditStatusDialogI18n);
  return (
    <Dialog
      open={true}
      onClose={close}
      maxWidth={'md'}
      fullWidth={true}
      scroll="body"
    >
      <DialogTitle>{t.EditUserStatus()}</DialogTitle>
      <DialogContent>
        <DialogContentText>
          {queryLoading === 'Loading' && (
            <div
              css={css`
                text-align: center;
              `}
            >
              <CircularProgress size={18} />
            </div>
          )}
          <div>
            {status && (
              <Autocomplete
                options={statusEnumValues}
                value={status}
                renderInput={params => <TextField {...params} />}
                onChange={(_, status) => setStatus(status)}
                disableClearable={true}
              />
            )}
          </div>
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={close}>{t.Close()}</Button>
        <LoadingStatusButton loadingStatus={updateLoading} onClick={save}>
          {t.Save()}
        </LoadingStatusButton>
      </DialogActions>
    </Dialog>
  );
};
