/** @jsxImportSource @emotion/react */
import { LoadingStateButton } from '../../../../../../common-components/LoadingButton';
import { roleEnumValues } from '../../../../../../domain/enums';
import { Errors } from '../../../../../../errors';
import { UserId } from '../../../../../../generated/domain/Ids.generated';
import {
  Role,
  UserInfos
} from '../../../../../../generated/domain/User.generated';
import { LoadingState } from '../../../../../../interfaces';
import { useGoTo } from '../../../../../../routing/routing-utils';
import { appContext } from '../../../../../../services/ApplicationContext';
import { state } from '../../../../../../state/state';
import { colors } from '../../../../../../styles/vars';
import { t } from './UserEditRolesDialog.i18n';
import { css } from '@emotion/react';
import { Warning as WarningIcon } from '@mui/icons-material';
import {
  Autocomplete,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
  Typography
} from '@mui/material';
import { PropsWithChildren, useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';

export const UserEditRolesDialog = (props: {
  userId: UserId;
  userInfos: UserInfos | undefined;
  updateUserInfos: (user: UserInfos) => void;
  loadingUserInfos: LoadingState;
}) => {
  const [roles, setRoles] = useState<Role[]>([]);
  const [updateLoading, setUpdateLoading] = useState<LoadingState>('Idle');
  const goTo = useGoTo();
  const loggedInUserInfos = useRecoilValue(state.userInfos);
  if (!loggedInUserInfos) {
    throw Errors._fe2e1fc7();
  }
  useEffect(() => setRoles(props.userInfos?.roles ?? []), [props.userInfos]);
  const close = () =>
    goTo({ name: 'UsersManagement/UserDetail', userId: props.userId });
  const save = () => {
    const userInfos = props.userInfos;
    if (!userInfos) {
      throw Errors._8ab803a9();
    }
    if (roles !== userInfos.roles) {
      setUpdateLoading('Loading');
      appContext.commandService
        .send({
          objectType: 'AdminUpdateRolesCommand',
          userId: userInfos.id,
          roles
        })
        .then(() => {
          setUpdateLoading('Idle');
          props.updateUserInfos({ ...userInfos, roles });
          close();
        });
    }
  };
  return (
    <Dialog
      open={true}
      onClose={close}
      maxWidth={'lg'}
      fullWidth={true}
      scroll="body"
    >
      <DialogTitle>{t.EditUserRoles()}</DialogTitle>
      <DialogContent>
        <DialogContentText>
          {props.loadingUserInfos === 'Loading' && (
            <div
              css={css`
                text-align: center;
              `}
            >
              <CircularProgress size={18} />
            </div>
          )}
          <div>
            <Autocomplete
              multiple
              options={roleEnumValues}
              getOptionLabel={role => role as any}
              value={roles as any[]}
              renderInput={params => <TextField {...params} />}
              onChange={(_, roles) => setRoles(roles)}
            />
            {props.userInfos &&
              loggedInUserInfos.id === props.userInfos.id &&
              !roles.includes('Admin') && (
                <WarningMessage>{t.WarningAdmin()}</WarningMessage>
              )}
            {props.userInfos &&
              !roles.includes('User') &&
              roles.includes('Admin') && (
                <WarningMessage>{t.WarningAdminWithNoUser()}</WarningMessage>
              )}
          </div>
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={close}>{t.Close()}</Button>
        <LoadingStateButton loadingState={updateLoading} onClick={save}>
          {t.Save()}
        </LoadingStateButton>
      </DialogActions>
    </Dialog>
  );
};

const WarningMessage = (props: PropsWithChildren) => (
  <div
    css={css`
      color: ${colors.white};
      background: ${colors.errorBackground};
      border: 1px solid ${colors.errorRed};
      border-radius: 4px;
      margin: 10px 0;
      padding: 10px;
      display: flex;
    `}
  >
    <WarningIcon
      css={css`
        margin-right: 20px;
      `}
    />
    <Typography>{props.children}</Typography>
  </div>
);