/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { ControlledPasswordInput } from 'view/components/form/ControlledPasswordInput';
import { useI18n } from 'hooks/i18n';
import { useForm } from 'react-hook-form';
import { LoadingStatusButton } from 'view/components/LoadingButton';
import { ResetLostPasswordFormI18n } from 'view/reset-lost-password/components/ResetLostPasswordFormI18n';
import { useState } from 'react';
import { LoadingStatus } from 'interfaces';
import { appContext } from 'services/ApplicationContext';
import { ResetLostPasswordCommandResponse } from 'generated/command/Commands.generated';
import { nominal } from 'utils/nominal-class';
import { navigateTo } from 'utils/routing-utils';
import { useUserState } from 'state/UserState';
import { UserAccountOperationToken } from 'generated/domain/UserAccountOperationToken.generated';

interface ResetLostPasswordFormRawInput {
  newPassword: string;
}

export const ResetLostPasswordForm = (props: {
  token: UserAccountOperationToken;
}) => {
  const [loading, setLoading] = useState<LoadingStatus>('Idle');
  const setUserInfos = useUserState(s => s.setUserInfos);
  const {
    handleSubmit,
    control,
    formState: { errors }
  } = useForm<ResetLostPasswordFormRawInput>();
  const t = useI18n(ResetLostPasswordFormI18n);
  return (
    <form
      onSubmit={handleSubmit(input => {
        setLoading('Loading');
        appContext.commandService
          .send<ResetLostPasswordCommandResponse>({
            objectType: 'ResetLostPasswordCommand',
            token: nominal(props.token),
            newPassword: nominal(input.newPassword)
          })
          .then(r => {
            setUserInfos(r.userInfos);
            navigateTo({ name: 'Root' });
          });
      })}
    >
      <div
        css={css`
          margin: 10px 0;
        `}
      >
        <ControlledPasswordInput
          name="newPassword"
          label={t.NewPassword()}
          control={control}
          errors={errors}
        />
      </div>
      <LoadingStatusButton loadingStatus={loading} type="submit">
        {t.ResetPassword()}
      </LoadingStatusButton>
    </form>
  );
};
