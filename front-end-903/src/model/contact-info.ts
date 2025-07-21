import {CommentContactInfo} from './commentContactInfo';

export enum FilterContactInfo {
  NOT_REPLIED = 'NOT_REPLIED',
  REPLIED = 'REPLIED'
}

export class ContactInfo {
  id: number | null;
  name: string;
  email: string;
  subject: string;
  message: string;
  status: FilterContactInfo;
  comment: CommentContactInfo[];
}
