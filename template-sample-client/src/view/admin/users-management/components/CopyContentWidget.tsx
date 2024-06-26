/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { FileCopy } from '@mui/icons-material';
import { Errors } from 'errors';
import { useRef, useState } from 'react';
import { clientUid } from 'utils';
import { pipe } from 'utils/Pipe';
import { NominalString } from 'utils/nominal-class';

const uuidClass = 'uuidClass-' + clientUid();
const copyContainerClass = 'copyContainer-' + clientUid();
const iconWidth = 40;

export const CopyContentWidget = (props: {
  text: string | NominalString<any>;
  limitChars?: number;
}) => {
  const [copyEffectId, setCopyEffectId] = useState(clientUid());
  const divRef = useRef<HTMLDivElement | null>(null);
  const onCopyClick = () => {
    if (!divRef.current) {
      throw Errors._587793ab();
    }
    // this feature is actually coming from hell
    // https://stackoverflow.com/questions/31593297/using-execcommand-javascript-to-copy-hidden-text-to-clipboard
    const tempInput = document.createElement('input');
    tempInput.style.position = 'absolute';
    tempInput.style.left = '-5000px';
    tempInput.style.top = '-5000px';
    tempInput.value = props.text;
    divRef.current.appendChild(tempInput);
    tempInput.select();
    document.execCommand('copy');
    divRef.current.removeChild(tempInput);
    setCopyEffectId(clientUid());
  };
  const display = pipe(props.text)
    .map(d => (props.limitChars ? d.substring(0, props.limitChars) : d))
    .unwrap();
  return (
    <div
      ref={divRef}
      css={css`
        display: inline-block;
        position: relative;
        cursor: pointer;

        &:hover {
          .${uuidClass} {
            opacity: 0.25;
          }

          .${copyContainerClass} {
            opacity: 1;
          }
        }
      `}
      onClick={onCopyClick}
    >
      <span className={uuidClass}>{display}</span>
      <span
        css={css`
          position: absolute;
          top: -4px;
          left: 50%;
          width: ${iconWidth}px;
          height: 28px;
          text-align: center;
          margin-left: -${iconWidth / 2}px;
          padding: 5px;
          opacity: 0;
        `}
        className={copyContainerClass}
      >
        <FileCopy
          key={copyEffectId}
          css={css`
            @keyframes fadeIn {
              to {
                opacity: 1;
              }
            }
            opacity: 0;
            animation: fadeIn 0.8s ease forwards;
            font-size: 20px;
          `}
        />
      </span>
    </div>
  );
};
