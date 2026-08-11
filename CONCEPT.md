# Core Requirements

- We add a Founder rank.
- Every Founder can claim one continuous Main Plot.
- Only the Founder can initially build/destroy on their Main Plot.
- The Founder is automatically an Owner of their Main Plot.
- The Founder can add/remove Owners, who gain the same build/destroy rights.
- The Founder can expand their Main Plot by claiming adjacent land, as long as the total area stays below the `max_blocks` threshold.
- A Founder can only have one continuous area of land; they cannot own disconnected Main Plots.
- The Founder can define Sub-Plots within their Main Plot.
- The Founder can manage Sub-Plots, including putting them up for sale and assigning ownership.
- Once a Sub-Plot is sold, the buyer becomes its sole owner.
- Main Plots are protected by default; players who aren't the Founder or an Owner cannot build/destroy within them.
- Sub-Plots override the Main Plot's ownership for their area; the Sub-Plot owner controls their own Sub-Plot.
- An unowned Sub-Plot inherits the permissions of its Main Plot.

## Permission Table

| Role           | Build/Destroy on `MP` | Expand `MP`        | Add/Remove Owners  | Create `SP`        | Build/Destroy on owned `SP`  |
|----------------|-----------------------|--------------------|--------------------|--------------------|------------------------------|
| Founder        | :white_check_mark:    | :white_check_mark: | :white_check_mark: | :white_check_mark: | :x:                          |
| Owner          | :white_check_mark:    | :x:                | :x:                | :x:                | :x:                          |
| Sub-Plot Owner | :x:                   | :x:                | :x:                | :x:                | :white_check_mark:           |
| Everyone else. | :x:                   | :x:                | :x:                | :x:                | :x:                          |

> `MP`: Main Plot
> `SP`: Sub-Plot

# Sub-Plot Sell

- Founder creates a Sub-Plot.
- Founder places a Plot sign inside/on it.
- Sets the price.
- Sign becomes a listing.

- Player right-clicks the sign.
- Plugin checks:
    - Is this a valid Plot sign?
    - Is the Sub-Plot still for sale?
    - Does the player have enough money?
- If yes:
    - Money is transferred.
    - Player becomes the Sub-Plot owner.
    - Plot is no longer for sale.
    - Sign is removed.

Example Sign:
```
[Plot]
<size>
<price>
<custom_message>
```
> If founder does not provide `<custom_message>`, it defaults to **For Sale!**.

# Core Datastructure

```yml
main-plots:
    plot0:
        world: "world"
        founder: "player1"
        owners:
            - "player2"
            - "player3"
        corner1:
            x: 10
            z: 10
        corner2:
            x: 100
            z: 100
        sub-plots:
            sub-plot0:
                owner: "player4"
                corner1:
                    x: 20
                    z: 20
                corner2:
                    x: 40
                    z: 40
```