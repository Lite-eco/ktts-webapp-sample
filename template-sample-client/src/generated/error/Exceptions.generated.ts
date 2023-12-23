import { Instant } from 'domain/datetime';
import { RequestErrorId } from 'generated/domain/Ids.generated';

export interface RequestError {
  id: RequestErrorId;
  status: number;
  error: string;
  message: string;
  instant: Instant;
}
