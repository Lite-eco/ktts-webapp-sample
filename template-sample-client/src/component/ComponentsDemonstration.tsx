/** @jsxImportSource @emotion/react */
import { LoadingState } from '../interfaces';
import {
  LoadingButton,
  LoadingStateButton
} from './base-component/LoadingButton';
import { css } from '@emotion/react';
import { Add } from '@mui/icons-material';
import * as React from 'react';
import { useState } from 'react';

export const ComponentsDemonstration = () => {
  const [loading, setLoading] = useState<LoadingState>('Idle');
  const onClick = () => {
    setLoading('Loading');
    const p = new Promise<void>((resolve, reject) => {
      setTimeout(() => {
        setLoading('Idle');
        resolve();
      }, 1000);
    });
    return p;
  };
  const props = {
    onClick: onClick,
    variant: 'outlined',
    startIcon: <Add />,
    css: css`
      font-weight: bold;
    `
  } as any;
  return (
    <>
      <h1>Components demonstration</h1>
      <div
        css={css`
          margin: 20px;
        `}
      >
        <LoadingButton {...props}>test</LoadingButton>
      </div>
      <div
        css={css`
          margin: 20px;
        `}
      >
        <LoadingStateButton {...props} loadingState={loading}>
          test
        </LoadingStateButton>
      </div>
    </>
  );
};
