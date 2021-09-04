THEATRE has ---- screens							</br>
SCREEN has ----- seats							</br>
SEAT has ------- rowNum + seatNum				</br>

MOVIE has ------ id + name									</br>
SHOW has ------- MOVIE + SCREEN + date + duration			</br>

BOOKING has ---- SHOW + list of seats booked + user + status



|  for ADMIN use  |         endpoint         |                              method                             |
|-----------------|--------------------------|-----------------------------------------------------------------|
| TheatreResource |  /createTheatre          | createTheatre(theatreName)                                      |
| TheatreResource |  /attachScreenInTheatre  | attachScreenInTheatre(screenName, theatreId)                    |
| TheatreResource |  /attachSeatInScreen     | attachSeatInScreen(rowNo, seatNo, screenId)                     |
| MovieResource   |  /createMovie            | createMovie(movieName)                                          |
| ShowResource    |  /createShow             | createShow(movieId, screenId, startTime, durationInSeconds)     |



|   for USER use   |         endpoint         |                    method                     |
|------------------|--------------------------|-----------------------------------------------|
| ShowResource     |  /getAvailableSeats      | getAvailableSeats(showId)                     |
| BookingResource  |  /createBooking          | createBooking(userId, showId, seatsIdList)    |
| PaymentsResource |  /paymentFailed          | paymentFailed(bookingId, userId)              |
| PaymentsResource |  /paymentSuccess         | paymentSuccess(bookingId, userId)             |

