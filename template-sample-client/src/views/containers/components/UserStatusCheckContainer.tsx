/** @jsxImportSource @emotion/react */
import { UserStatus } from '../../../generated/domain/User.generated';
import { GetUserStatusQueryResponse } from '../../../generated/query/Queries.generated';
import { useGoTo } from '../../../routing/routing-utils';
import { appContext } from '../../../services/ApplicationContext';
import { state } from '../../../state/state';
import { assertUnreachable } from '../../../utils';
import { asNominalString } from '../../../utils/nominal-class';
import { t } from './UserStatusCheckContainer.i18n';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle
} from '@mui/material';
import { PropsWithChildren, ReactNode, useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';

const userStatusContent = (
  status: UserStatus | undefined,
  children: ReactNode
) => {
  switch (status) {
    case undefined:
    case 'Active':
      return children;
    case 'MailValidationPending':
      return <div>{t.MailMustBeValidated()}</div>;
    case 'Disabled':
      return <div>{t.DisabledAccount()}</div>;
    default:
      assertUnreachable(status);
  }
};

export const UserStatusCheckContainer = (props: PropsWithChildren) => {
  const [userInfos, setUserInfos] = useRecoilState(state.userInfos);
  const [displayPopup, setDisplayPopup] = useState(false);
  const goTo = useGoTo();
  useEffect(() => {
    if (userInfos?.status === 'MailValidationPending') {
      const intervalId = setInterval(() => {
        appContext.queryService
          .send<GetUserStatusQueryResponse>({
            objectType: 'GetUserStatusQuery'
          })
          .then(r => {
            if (r.status !== 'MailValidationPending') {
              clearInterval(intervalId);
            }
            setUserInfos({ ...userInfos, status: r.status });
          });
      }, 2000);
      return () => clearInterval(intervalId); //This is important
    }
    return () => {};
  }, [userInfos, setUserInfos]);
  const mailValidationToken = new URLSearchParams(window.location.search).get(
    'mailValidation'
  );
  if (mailValidationToken) {
    const [token, userId] = mailValidationToken.split('-');
    appContext.commandService
      .send({
        objectType: 'ValidateMailCommand',
        token: asNominalString(token)
      })
      .then(() => {
        // validation is done whether the user is connected or not
        // (and if there's a session, it's not necessarily with the validated user)
        if (userInfos?.id === userId) {
          setUserInfos({
            ...userInfos,
            status: 'Active'
          });
        }
        goTo({ name: 'Root' }, { replace: true });
        setDisplayPopup(true);
      });
  }
  return (
    <>
      {userStatusContent(userInfos?.status, props.children)}
      <Dialog open={displayPopup} onClose={() => setDisplayPopup(false)}>
        <DialogTitle>{t.UserAccountMailValidation()}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            {t.TheMailHasBeenValidated()}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDisplayPopup(false)} autoFocus>
            {t.ok()}
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
