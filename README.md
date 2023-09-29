# Unofficial codes for [Grokking Streaming Systems](https://www.manning.com/books/grokking-streaming-systems)

## Checkpoint

This repository iterates through the book's chapters. 
Readers can use `git reset --hard COMMIT` to view the corresponding implementation of the functionalities introduced in each chapter.

Here is the mapping of each functionality:

| Chapter                                                | Git commit                                                      |
|--------------------------------------------------------|-----------------------------------------------------------------|
| CH-02                                                  | `5342c67606038422049032e7cc30e3f21ba4aa05`                      |
| CH-03                                                  | `e40163cf0febf9e057c338acb9daffe46b6021ef`                      |
| CH-04                                                  | `a72cfe61dd55ff4c1aa1b46524dd4fb8cecc633c`                      |
| CH-05 : at-least-once implementation with `acknowledger` | Use the latest codes of the branch `acknowledger_at_least_once` |

- The at-least-once implementation with `acknowledger` differs from what the book suggests.
In this approach, the `acknowledger` is employed to oversee the event cache for each incoming queue.
If an event is processed successfully, the cache is deleted; otherwise, the event is placed back into the incoming queue.