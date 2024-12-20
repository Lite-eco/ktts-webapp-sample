/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { Button, CircularProgress } from '@mui/material';
import { ButtonTypeMap } from '@mui/material/Button/Button';
import { ClientUid } from 'domain/client-ids';
import { EmotionStyles, LoadingStatus } from 'interfaces';
import * as React from 'react';
import { PropsWithChildren, useState } from 'react';
import { assertUnreachable } from 'utils';

const ButtonContent = (
  props: PropsWithChildren<{
    loadingStatus: LoadingStatus;
  }>
) => {
  switch (props.loadingStatus) {
    case 'Idle':
    case 'Error':
      return <>{props.children}</>;
    case 'Loading':
      return (
        <>
          <div
            css={css`
              // so button sizing doesn't change
              visibility: hidden;
            `}
          >
            {props.children}
          </div>
          <div
            css={css`
              position: absolute;
              text-align: center;
              padding-top: 6px;
            `}
          >
            <CircularProgress size={18} />
          </div>
        </>
      );
    default:
      assertUnreachable(props.loadingStatus);
  }
};

const LoadingButtonBase = (
  props: PropsWithChildren<{
    onClick?: () => any;
    loadingStatus: LoadingStatus;
    type?: 'submit';
    variant?: ButtonTypeMap['props']['variant'];
    addCss?: EmotionStyles;
    formId?: ClientUid;
  }>
) => (
  <Button
    onClick={props.onClick}
    type={(props.type ?? props.formId) ? 'submit' : undefined}
    variant={props.variant ?? 'contained'}
    disabled={props.loadingStatus === 'Loading'}
    css={props.addCss}
    form={props.formId}
  >
    <ButtonContent loadingStatus={props.loadingStatus}>
      {props.children}
    </ButtonContent>
  </Button>
);

export const LoadingButton = (
  props: PropsWithChildren<{
    onClick: () => Promise<any>;
    type?: 'submit';
    variant?: ButtonTypeMap['props']['variant'];
    addCss?: EmotionStyles;
  }>
) => {
  const [loading, setLoading] = useState<LoadingStatus>('Idle');
  return (
    <LoadingButtonBase
      onClick={() => {
        setLoading('Loading');
        props
          .onClick()
          .then(() => setLoading('Idle'))
          .catch(() => setLoading('Error'));
      }}
      loadingStatus={loading}
      type={props.type}
      variant={props.variant}
      addCss={props.addCss}
    >
      {props.children}
    </LoadingButtonBase>
  );
};

export const LoadingStatusButton = (
  props: PropsWithChildren<{
    onClick?: () => any;
    loadingStatus: LoadingStatus;
    type?: 'submit';
    variant?: ButtonTypeMap['props']['variant'];
    addCss?: EmotionStyles;
    formId?: ClientUid;
  }>
) => (
  <LoadingButtonBase
    onClick={props.onClick}
    loadingStatus={props.loadingStatus}
    type={props.type}
    variant={props.variant}
    addCss={props.addCss}
    formId={props.formId}
  >
    {props.children}
  </LoadingButtonBase>
);
