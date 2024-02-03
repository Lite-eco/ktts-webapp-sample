/** @jsxImportSource @emotion/react */
import { UpdatePasswordCommandResponse } from 'generated/command/Commands.generated';
import { appContext } from 'services/ApplicationContext';
import { PasswordFormInput } from 'view/account/components/UpdatePasswordForm';
import { UpdatePasswordDialogButton } from 'view/account/components/UpdatePasswordDialogButton';

export const AccountRoute = () => {
  const onSubmit = (dto: PasswordFormInput) =>
    appContext.commandService
      .send<UpdatePasswordCommandResponse>({
        objectType: 'UpdatePasswordCommand',
        ...dto
      })
      .then(r => r.result);
  return <UpdatePasswordDialogButton onSubmit={onSubmit} />;
};
