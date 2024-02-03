/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle
} from '@mui/material';
import { LoadingStatusButton } from 'view/components/LoadingButton';
import { UpdatePasswordResult } from 'generated/domain/User.generated';
import { useI18n } from 'hooks/i18n';
import { LoadingStatus } from 'interfaces';
import { useState } from 'react';
import { colors } from 'style/vars';
import { assertUnreachable, clientUid } from 'utils';
import {
  UpdatePasswordForm,
  PasswordFormInput
} from 'view/account/components/UpdatePasswordForm';
import { UpdatePasswordDialogButtonI18n } from 'view/account/components/UpdatePasswordDialogButton.i18n';

export const UpdatePasswordDialogButton = (props: {
  onSubmit: (dto: PasswordFormInput) => Promise<UpdatePasswordResult>;
}) => {
  const [open, setOpen] = useState(false);
  const [updateLoading, setUpdateLoading] = useState<LoadingStatus>('Idle');
  const [displayError, setDisplayError] = useState(false);
  const close = () => setOpen(false);
  const onSubmit = (dto: PasswordFormInput) => {
    setUpdateLoading('Loading');
    setDisplayError(false);
    return props.onSubmit(dto).then(r => {
      setUpdateLoading('Idle');
      switch (r) {
        case 'BadPassword':
          setDisplayError(true);
          break;
        case 'PasswordChanged':
          close();
          break;
        default:
          assertUnreachable(r);
      }
    });
  };
  const formId = clientUid();
  const t = useI18n(UpdatePasswordDialogButtonI18n);
  return (
    <>
      <Button onClick={() => setOpen(true)}>{t.UpdatePassword()}</Button>
      <Dialog
        open={open}
        onClose={close}
        maxWidth={'sm'}
        fullWidth={true}
        scroll="body"
      >
        <DialogTitle>{t.UpdatePassword2()}</DialogTitle>
        <DialogContent>
          <DialogContentText>
            <UpdatePasswordForm formId={formId} onSubmit={onSubmit} />
            {displayError && (
              <div
                css={css`
                  color: ${colors.white};
                  background: ${colors.errorBackground};
                  border: 1px solid ${colors.errorRed};
                  border-radius: 4px;
                  margin: 10px 0;
                  padding: 10px;
                `}
              >
                {t.BadPassword()}
              </div>
            )}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={close}>{t.Close()}</Button>
          <LoadingStatusButton formId={formId} loadingStatus={updateLoading}>
            {t.Save()}
          </LoadingStatusButton>
        </DialogActions>
      </Dialog>
    </>
  );
};
