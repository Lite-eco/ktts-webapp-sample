/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { LoadingStatusButton } from 'view/components/LoadingButton';
import { ControlledPasswordInput } from 'view/components/form/ControlledPasswordInput';
import { ControlledTextInput } from 'view/components/form/ControlledTextInput';
import { PlainStringPassword } from 'generated/domain/Security.generated';
import { useI18n } from 'hooks/i18n';
import { LoadingStatus } from 'interfaces';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { nominal } from 'utils/nominal-class';
import { LoginFormI18n } from 'view/login/components/LoginForm.i18n';
import { RouteLink } from 'view/components/RouteLink';

export interface LoginFormInput {
  mail: string;
  password: PlainStringPassword;
}

interface LoginFormRawInput {
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
  const [loading, setLoading] = useState<LoadingStatus>('Idle');
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
      <div
        css={css`
          text-align: right;
        `}
      >
        <RouteLink path={{ name: 'LostPassword' }}>
          {t.LostPassword()}
        </RouteLink>
      </div>
      <LoadingStatusButton loadingStatus={loading} type="submit">
        {t.Login()}
      </LoadingStatusButton>
    </form>
  );
};
