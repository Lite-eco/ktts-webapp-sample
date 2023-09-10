/** @jsxImportSource @emotion/react */
import { LoadingStateButton } from '../../../common-components/LoadingButton';
import { ControlledPasswordInput } from '../../../common-components/form/ControlledPasswordInput';
import { ControlledTextInput } from '../../../common-components/form/ControlledTextInput';
import { PlainStringPassword } from '../../../generated/domain/Security.generated';
import { useI18n } from '../../../hooks/i18n';
import { LoadingState } from '../../../interfaces';
import { nominal } from '../../../utils/nominal-class';
import { LoginFormI18n } from './LoginForm.i18n';
import { css } from '@emotion/react';
import { useState } from 'react';
import { useForm } from 'react-hook-form';

export interface LoginFormInput {
  mail: string;
  password: PlainStringPassword;
}

export interface LoginFormRawInput {
  mail: string;
  password: string;
}

export const LoginForm = (props: {
  onSubmit: (dto: LoginFormInput) => Promise<void>;
}) => {
  const {
    handleSubmit,
    control,
    formState: { errors }
  } = useForm<LoginFormRawInput>();
  const [loading, setLoading] = useState<LoadingState>('Idle');
  const t = useI18n(LoginFormI18n);
  return (
    <form
      onSubmit={handleSubmit(input => {
        setLoading('Loading');
        props
          .onSubmit({
            mail: input.mail,
            password: nominal(input.password)
          })
          .then(() => setLoading('Idle'))
          .catch(() => setLoading('Error'));
      })}
    >
      <div
        css={css`
          margin: 10px 0;
        `}
      >
        <ControlledTextInput
          name="mail"
          label={t.Email()}
          control={control}
          errors={errors}
        />
      </div>
      <div
        css={css`
          margin: 10px 0;
        `}
      >
        <ControlledPasswordInput
          name="password"
          label={t.Password()}
          control={control}
          errors={errors}
        />
      </div>
      <LoadingStateButton loadingState={loading} type="submit">
        {t.Login()}
      </LoadingStateButton>
    </form>
  );
};
