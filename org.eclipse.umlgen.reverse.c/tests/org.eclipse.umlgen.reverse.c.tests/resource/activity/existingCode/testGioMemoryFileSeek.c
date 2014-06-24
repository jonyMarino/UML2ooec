off_t GioMemoryFileSeek(struct Gio  *vself,
                        off_t       offset,
                        int         whence) {
  struct GioMemoryFile  *self = (struct GioMemoryFile *) vself;
  size_t                 new_pos = (size_t) -1;

  switch (whence) {
    case SEEK_SET:
      new_pos = offset;
      break;
    case SEEK_CUR:
      new_pos = self->curpos + offset;
      break;
    case SEEK_END:
      new_pos = (size_t) (self->len + offset);
      break;
    default:
      errno = EINVAL;
      break;
  }
  /**
   * on error, new_pos should be SIZE_T_MAX. On overflow it will either
   * be greater than self->len, or will have wrapped around.
   * TODO (ilewis): Detect wraparound and return an error.
   */
  if (new_pos > self->len) {
    errno = EINVAL;
    return (off_t) -1;
  }
  self->curpos = new_pos;
  return (off_t) new_pos;
}
